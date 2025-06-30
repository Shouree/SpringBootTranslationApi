package com.example.translation_library.mappers;

public interface Mapper<A, B> {
    
    B mapTo(A a);
    
    A mapFrom(B b);
}
