package com.gap.faultTolerance;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;

import java.util.concurrent.TimeUnit;

public class SupervisorMain {
    public static void main(String[] args) throws Exception {
        ActorSystem actorSystem= ActorSystem.create();
        ActorRef supervisorActor= actorSystem.actorOf(Props.create(SupervisorActor.class), "SupervisorActor");
        ActorRef childActor;
        Props childActorProps= Props.create(ChildActor.class);
        Timeout timeout= new Timeout(5, TimeUnit.SECONDS);

        childActor=(ActorRef) Await.result(Patterns.ask(supervisorActor,childActorProps,5000), timeout.duration());

        System.out.println(supervisorActor.path());
        System.out.println(childActor.path());

        childActor.tell("Hello Child Actor",ActorRef.noSender());
        childActor.tell(new Exception(),ActorRef.noSender());

    }
}
