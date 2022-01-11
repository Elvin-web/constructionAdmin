package az.elvin.thread.v3.threadClass;

import az.elvin.thread.entity.BrendEntity;
import az.elvin.thread.repo.BrendRepository;

import java.util.List;

public class ThreadV2 implements Runnable {

    private final BrendRepository brendRepository;

    public ThreadV2(BrendRepository brendRepository) {
        this.brendRepository = brendRepository;
    }

    @Override
    public void run() {

        try {
            List<BrendEntity> brendEntityList = brendRepository.findAll();
            System.out.println(brendEntityList);
        } catch (Exception e) {

            System.out.println("Exception is caught" + e);
        }
    }
}
