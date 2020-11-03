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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AtmBot extends TelegramLongPollingBot {

    private static final String BANK_INFO_OUTPUT_FORMAT = "%d. %s";

    private static final float MAX_DISTANCE = 1;

    private final CompanyClient companyClient;
    private final ApplicationProperties applicationProperties;

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Location location;
        SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId());
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
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
        } else if ((location = update.getMessage().getLocation()) != null) { // location is shared
            val index = new AtomicInteger(0);
            val atms = companyClient.getAtms(location, MAX_DISTANCE);
            message.setText(
                    "Your current location is [long: " + location.getLongitude() + ", lat: " + location.getLatitude() + "].\n\n" +
                    String.format("Atms %s located near %.2f km from current position:\n", atms.size() == 1 ? "is" : "are", MAX_DISTANCE) +
                    atms.stream()
                        .map(company -> {
                            final int currentIndex = index.incrementAndGet();
                            return String.format(BANK_INFO_OUTPUT_FORMAT, currentIndex, atms.get(currentIndex - 1).getAddress());
                        })
                        .collect(Collectors.joining("\n")));
        }

        execute(message);
    }

    public void attachLocationButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardButton shareNumBtn = new KeyboardButton("Send current location");
        shareNumBtn.setRequestContact(false);
        shareNumBtn.setRequestLocation(true);
        keyboardSecondRow.add(shareNumBtn);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
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
