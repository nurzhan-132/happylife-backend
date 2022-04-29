package com.happylife.library.myspringbootproject.security.services.library;

import java.io.IOException;
import java.util.List;
import com.happylife.library.myspringbootproject.dto.PeriodicalDTO;
import com.happylife.library.myspringbootproject.dto.SubscriberDTO;

import com.happylife.library.myspringbootproject.payload.request.PostNewPeriodicalRequest;

public interface LibraryService {

    List<PeriodicalDTO> getAllPeriodicals();

    public List<SubscriberDTO> getSubscribersOfPeriodicalId(Long id);

    public void postNewPeriodical(PostNewPeriodicalRequest request) throws IOException;

    void subscribeTo(Long periodicalId, String username);

    void unsubscribeFrom(Long periodicalId, String username);

    void deletePeriodical(Long periodicalId);

    void updateAvailability(Long periodicalId);

    String showImpl();

//    List<SubscriptionsDTO> getSubscriptions(long id);

}
