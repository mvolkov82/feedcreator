package com.okeandra.demo.services.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AllFeedsStarter implements Processing{

    @Autowired
    FeedInsales feedInsales;

    @Autowired
    SberFeedAll0DayKapousOllinOneDay sberFeedAll0DayKapousOllinOneDay;

    @Autowired
    FeedSberDutyFree feedSberDutyFree;

    @Autowired
    FeedGroupPrice feedGroupPrice;

    @Autowired
    FeedSelvis feedSelvis;

    @Autowired
    private FeedYandex feedYandex;

    @Autowired
    private FeedOzon feedOzon;



    @Override
    public List<String> start() {
        List<Processing> todoList = new ArrayList<>();

        feedInsales.setUseCache(false);
        todoList.add(feedInsales);

        sberFeedAll0DayKapousOllinOneDay.setUseCache(true);
        todoList.add(sberFeedAll0DayKapousOllinOneDay);

        feedSberDutyFree.setUseCache(true);
        todoList.add(feedSberDutyFree);

        feedGroupPrice.setUseCache(true);
        todoList.add(feedGroupPrice);

        feedSelvis.setUseCache(true);
        todoList.add(feedSelvis);

        feedYandex.setUseCache(true);
        todoList.add(feedYandex);

        feedOzon.setUseCache(true);
        todoList.add(feedOzon);

        todoList.forEach(Processing::start);
        List<String> result = new ArrayList<>(Arrays.asList("Все фиды созданы успешно"));
        return result;
    }



}
