package com.happylife.library.myspringbootproject.security.services.library;

import com.happylife.library.myspringbootproject.models.Periodical;
import com.happylife.library.myspringbootproject.models.User;
import com.happylife.library.myspringbootproject.payload.request.PostNewPeriodicalRequest;
import com.happylife.library.myspringbootproject.repository.OrdersRepository;
import com.happylife.library.myspringbootproject.repository.PeriodicalRepository;
import com.happylife.library.myspringbootproject.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.happylife.library.myspringbootproject.dto.PeriodicalDTO;
import com.happylife.library.myspringbootproject.dto.SubscriberDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class LibraryServiceImpl implements LibraryService {

    private final PeriodicalRepository periodicalRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    public LibraryServiceImpl(
            @Autowired UserRepository userRepository,
            @Autowired OrdersRepository ordersRepository,
            @Autowired PeriodicalRepository periodicalRepository
    ) {

        this.periodicalRepository = periodicalRepository;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<PeriodicalDTO> getAllPeriodicals() {
        return periodicalRepository
                .getAllPeriodicals()
                .stream()
                .map(this::convertToPeriodicalDTO).collect(Collectors.toList());
    }

    private PeriodicalDTO convertToPeriodicalDTO(Periodical periodical) {
        PeriodicalDTO periodicalDTO = new PeriodicalDTO();
        return periodicalDTO
                .setId(periodical.getId())
                .setPublisher(periodical.getPublisher())
                .setName(periodical.getName())
                .setDescription(periodical.getDescription())
                .setAvailable(periodical.getAvailable())
                .setImage(periodical.getImage())
                .setType(periodical.getType())
                .setSubscribers(userRepository
                        .get_Subscribers_Of_Periodical_Id(periodical.getId())
                        .stream()
                        .map(this::convertToSubscriberDTO).collect(Collectors.toList()));
    }

    private SubscriberDTO convertToSubscriberDTO(User users) {
        SubscriberDTO subscriberDTO = new SubscriberDTO();
        return subscriberDTO
                .setId(users.getId())
                .setFirstName(users.getFirstName())
                .setLastName(users.getLastName());
    }

    @Transactional
    public List<SubscriberDTO> getSubscribersOfPeriodicalId(Long id) {
        return userRepository
                .get_Subscribers_Of_Periodical_Id(id)
                .stream()
                .map(this::convertToSubscriberDTO).collect(Collectors.toList());
    }

    @Override
    public void postNewPeriodical(PostNewPeriodicalRequest request) {

        Optional<MultipartFile> fileOpt = Optional.ofNullable(request.getFile());
        if (fileOpt.isPresent() && fileOpt.get().getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(fileOpt.get().getOriginalFilename());
            if (fileName.contains("..")) {
                log.warn("IN is not a valid file");
            }
        }
        try {
            Periodical periodical = new Periodical();

            periodical.setPublisher(request.getPublisher());
            periodical.setName(request.getName());
            periodical.setDescription(request.getDescription());
            periodical.setAvailable(request.getAvailable());
            periodical.setImage(request.getFile().getBytes());

            periodicalRepository.save(periodical);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void subscribeTo(Long periodicalId, String username) {
        periodicalRepository.subscribeTo(periodicalId, username);
    }

    @Override
    @Transactional
    public void unsubscribeFrom(Long periodicalId, String username) {
        periodicalRepository.unsubscribeFrom(periodicalId, username);
    }

    @Override
    public void deletePeriodical(Long periodicalId) {
        periodicalRepository.deletePeriodical(periodicalId);
    }

    @Override
    public void updateAvailability(Long periodicalId) {
        periodicalRepository.updateAvailability(periodicalId);
    }

    @Override
    public String showImpl() {
        return "initial";
    }

//    @Override
//    @Transactional
//    public List<SubscriptionsDTO> getSubscriptions(long id) {
//        return userRepository.getSubscriptions(id);
//    }
}
