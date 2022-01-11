package az.elvin.constructionAdmin.v2.threadClass;

import az.elvin.constructionAdmin.repo.BrendRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadV2 implements Runnable{

    @Autowired
    BrendRepository brendRepository;

    @Override
    public void run() {

        try {

            System.out.println(brendRepository.findAll());
            System.out.println(
                    "Thread " + Thread.currentThread().getId()
                            + " is running");
        } catch (Exception e) {

            System.out.println("Exception is caught"+e);
        }
    }
}
