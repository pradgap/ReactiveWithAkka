package com.gap.faultTolerance;

import akka.actor.AbstractActor;


public class ChildActor extends AbstractActor {
    @Override
    public void postStop() throws Exception {
        System.out.println("Calling PostStop function");
//        super.postStop();
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("Calling PreStart function in Child Actor");
//        super.preStart();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, str->System.out.println(str))
                .match(Exception.class, e-> { throw new Exception();})
                .build();

    }
}
