package com.bobocode.bring.testdata.di.positive.listsetterinjector;

import com.bobocode.bring.core.anotation.Component;

@Component
public class C implements IA {

    @Override
    public void talk() {
        System.out.println("Talk from C");
    }
}