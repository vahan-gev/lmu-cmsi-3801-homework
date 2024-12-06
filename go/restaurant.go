package main

import (
	"log"
	"math/rand"
	"sync"
	"sync/atomic"
	"time"
)

// A little utility that simulates performing a task for a random duration.
// For example, calling do(10, "Remy", "is cooking") will compute a random
// number of milliseconds between 5000 and 10000, log "Remy is cooking",
// and sleep the current goroutine for that much time.
func do(seconds int, action ...any) {
	log.Println(action...)
	randomMillis := 500*seconds + rand.Intn(500*seconds)
	time.Sleep(time.Duration(randomMillis) * time.Millisecond)
}

type Order struct {
	id         uint64
	customer   string
	preparedBy string
	reply      chan *Order
}

var nextId atomic.Uint64
var waiter = make(chan *Order, 3)

func cook(name string) {
	log.Println(name, "starting work")
	for {
		order := <-waiter
		do(10, name, "cooking order", order.id, "for", order.customer)
		order.preparedBy = name
		order.reply <- order
	}
}

func customer(name string, wg *sync.WaitGroup) {
	defer wg.Done()
	mealsEaten := 0

	for mealsEaten < 5 {
		order := &Order{
			id:       nextId.Add(1),
			customer: name,
			reply:    make(chan *Order),
		}
		log.Println(name, "placed order", order.id)

		select {
		case waiter <- order:
			meal := <-order.reply
			do(2, name, "eating cooked order", meal.id, "prepared by", meal.preparedBy)
			mealsEaten++
		case <-time.After(7 * time.Second):
			do(5, name, "waiting too long, abandoning order", order.id)
			time.Sleep(time.Duration(2500+rand.Intn(2500)) * time.Millisecond)
		}
	}
	log.Println(name, "going home")
}

func main() {
	customers := []string{"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai"}
	cooks := []string{"Remy", "Colette", "Linguini"}

	var wg sync.WaitGroup
	wg.Add(len(customers))

	for _, cookName := range cooks {
		go cook(cookName)
	}

	for _, customerName := range customers {
		go customer(customerName, &wg)
	}

	wg.Wait()
	log.Println("Restaurant closing")
}
