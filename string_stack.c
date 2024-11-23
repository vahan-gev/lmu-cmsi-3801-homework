#include "string_stack.h"
#include <stdlib.h>
#include <string.h>
// Complete your string stack implementation in this file.

typedef struct _Stack {
    char elements[MAX_CAPACITY][MAX_ELEMENT_BYTE_SIZE];
    int top;
} Stack;

static void initialize(struct _Stack* s) {
    s->top = -1;
}

bool is_full(const stack s) {
    if (s == NULL) return false;
    return ((Stack*)s)->top == MAX_CAPACITY - 1;
}

bool is_empty(const stack s) {
    if (s == NULL) return true;
    return ((Stack*)s)->top == -1;
}

int size(const stack s) {
    if (s == NULL) return 0;
    return ((Stack*)s)->top + 1;
}


stack_response create() {
    stack_response response;
    Stack * s = (Stack*)malloc(sizeof(Stack));

    //If memory allocation fails, return out_of_memory and sets response of stack to NULL
    if (s == NULL) {
        response.code = out_of_memory;
        response.stack = NULL;
        return response;
    }
    //Otherwise, initialize the stack and set the response of stack to the stack
    initialize(s);
    response.code = success;
    response.stack = s;

    return response;
}

response_code push(stack s, char* item) {

    if(s == NULL) return out_of_memory;
    Stack* stack = (Stack*)s;

    if(is_full(s)) return stack_full;
    size_t lenght_of_item = strlen(item);

    if (lenght_of_item >= MAX_ELEMENT_BYTE_SIZE) return stack_element_too_large;
    stack->top++;

    strncpy(stack->elements[stack->top], item, MAX_ELEMENT_BYTE_SIZE - 1);
    stack->elements[stack->top][MAX_ELEMENT_BYTE_SIZE - 1] = '\0';
    return success;
}

string_response pop(stack s) {
    string_response response;
    if (s == NULL) {
        response.code = out_of_memory;
        response.string = NULL;
        return response;
    }
    Stack* stack = (Stack*)s;

    if (is_empty(s)) {
        response.code = stack_empty;
        response.string = NULL;
        return response;
    }

    char* item = malloc(MAX_ELEMENT_BYTE_SIZE);
    if (item == NULL) {
        response.code = out_of_memory;
        response.string = NULL;
        return response;
    }

    strncpy(item, stack->elements[stack->top], MAX_ELEMENT_BYTE_SIZE - 1);
    item[MAX_ELEMENT_BYTE_SIZE - 1] = '\0';
    stack->top--;

    response.code = success;
    response.string = item;
    return response;
}


void destroy(stack* s) {
    if(s == NULL || *s == NULL) return;
    free(*s);
    *s = NULL;
}







