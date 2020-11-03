package ru.vtb.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.dto.CompanyDto;
import ru.vtb.feign.CompanyClient;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AtmBot extends TelegramLongPollingBot {

    private static final String BANK_INFO_OUTPUT_FORMAT = "%d. %s";
    private static final float MAX_DISTANCE = 1;
    private static final String NEW_LINE = "\n";

    private final CompanyClient companyClient;
    private final ApplicationProperties applicationProperties;

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) return;

        Location location;
        val inputMessage = update.getMessage();
        val message = new SendMessage().setChatId(inputMessage.getChatId());
        // We check if the update has a message and the message has text
        if (inputMessage.hasText()) {
            switch (inputMessage.getText()) {
                case "/start": {
                    message.setText("Welcome to Reactive ATM bot! Use command /location to send your location");
                    break;
                }
                case "/location": {
                    message.setText("Share your location just push on the button");
                    attachLocationButton(message);
                    break;
                }
                default: {
                    message.setText("Command is not recognized. Use /location for any cases.");
                    break;
                }
            }
        } else if ((location = inputMessage.getLocation()) != null) { // location is shared
            val index = new AtomicInteger(0);
            val atms = companyClient.getAtms(location, MAX_DISTANCE);
            message.setText(
                    String.format("Your current location is https://yandex.ru/maps?text=%.6f,%.6f", location.getLatitude(), location.getLongitude()) + NEW_LINE + NEW_LINE +
                    (atms.isEmpty()
                            ? String.format("There is no atm in location of %.2f km from current position", MAX_DISTANCE)
                            : (atms.contains(CompanyDto.NO_COMPANY) ? "Just try again later" :
                                String.format("Atms %s located near %.2f km from current position:", atms.size() == 1 ? "is" : "are", MAX_DISTANCE) + NEW_LINE +
                                    atms.stream()
                                        .map(CompanyDto::getAddress)
                                        .distinct() // по 1 адресу может находиться несколько банкоматов
                                        .map(company -> String.format(BANK_INFO_OUTPUT_FORMAT, index.incrementAndGet(), company))
                                        .collect(Collectors.joining(NEW_LINE)))
                    )
            );
        }
        // send answer to telegram
        execute(message);
    }

    public void attachLocationButton(SendMessage sendMessage) {
        val replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        val keyboardRow = new KeyboardRow();
        val shareLocationButton = new KeyboardButton("Send current location");
        shareLocationButton.setRequestContact(false);
        shareLocationButton.setRequestLocation(true);
        keyboardRow.add(shareLocationButton);
        replyKeyboardMarkup.setKeyboard(Arrays.asList(keyboardRow));
    }

    @Override
    public String getBotToken() {
        return applicationProperties.getToken();
    }

    @Override
    public String getBotUsername() {
        return applicationProperties.getName();
    }
}
