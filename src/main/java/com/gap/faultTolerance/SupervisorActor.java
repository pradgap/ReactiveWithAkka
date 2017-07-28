package com.gap.faultTolerance;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.escalate;

public class SupervisorActor extends AbstractActor {
    private static SupervisorStrategy supervisorStrategy=
            new OneForOneStrategy(3, Duration.create("5 seconds"), DeciderBuilder
                    .match(Exception.class, e->restart()).
                    matchAny(o-> escalate())
                    .build());


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Props.class, props->{
                    getSender().tell(getContext().actorOf(props, "childActor"), getSelf());
                })
                .build();
    }
}
