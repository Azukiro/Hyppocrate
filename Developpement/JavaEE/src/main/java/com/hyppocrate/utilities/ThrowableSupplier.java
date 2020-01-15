package com.hyppocrate.utilities;

public interface ThrowableSupplier<T> {
    T get() throws Exception;
}
