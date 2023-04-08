package com.example.bisneslogic.util;

import com.example.bisneslogic.models.Category;

public class CategoryNotCreatedException extends RuntimeException{
    public CategoryNotCreatedException(String msg){
        super(msg);
    }
}
