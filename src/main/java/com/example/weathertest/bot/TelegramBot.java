package com.example.weathertest.bot;

import com.example.weathertest.config.BotConfig;
import com.example.weathertest.models.Model;
import com.example.weathertest.models.ModelRepo;
import com.example.weathertest.service.ModelService;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private ModelRepo modelRepo;
    @Autowired
    private ModelService modelService;
    Model model = new Model();
    static final String ERROR_TEXT = "Error occurred: ";
    static final String HELP_TEXT = "Этот бот пока очень тупой!!!";
    private String url = "https://api.weather.yandex.ru/v2/forecast/?lat=47.222078&lon=39.720349&lang=ru_RU";
    private String token = "0b3db0b2-37a5-441d-8e80-77a44fecda2f";

    private final BotConfig botConfig;

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "Запуск бота"));
        commandList.add(new BotCommand("/forecast", "Получить прогноз на сегодня"));
        commandList.add(new BotCommand("/help", "Узнать, что может этот бот"));
        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> {deleteForecast(chatId);break;}  //         addForecast(chatId, url, token);
                case "/forecast" -> {addForecast(chatId, url, token);       //    prepareAndSendMessage(chatId, modelRepo.findAll().toString());
                    prepareAndSendMessage(chatId, modelService.findAll().toString());break;}    // showForecast(chatId, model);
                case "/help" -> {prepareAndSendMessage(chatId, HELP_TEXT);}
                default -> {defaultResponse(chatId);}
            }
        }
    }

    private void addForecast(long chatId, String url, String token) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Yandex-API-Key", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result = response.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONObject structureGeo = (JSONObject) jsonObject.get("geo_object");
        JSONObject structureFact = (JSONObject) jsonObject.get("fact");

        String strGeo = String.valueOf(structureGeo.get("locality"));

        String strFactPressureMm = structureFact.get("pressure_mm") + " мм";
        String strFactWindSpeed = structureFact.get("wind_speed") + " м/с";
        String strFactTemp = structureFact.get("temp") + " \u2103";
        String strFactCondition = String.valueOf(structureFact.get("condition"));

        String resGeo = strGeo.substring(9, 23);

        model.setГород(resGeo);
        model.setТемпература(strFactTemp);
        model.setПогода(strFactCondition);
        model.setСкорость_ветра(strFactWindSpeed);
        model.setДавление(strFactPressureMm);
        model.setДата_запроса(new Date(System.currentTimeMillis()));

        modelRepo.save(model);

    }
    private void prepareAndSendMessage(long chaId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setText(textToSend);
        message.setChatId(String.valueOf(chaId));
        executeMessage(message);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void deleteForecast(long chatId) {
        modelRepo.deleteAll();
    }

    private void defaultResponse(long chatId) {
        String answer = HELP_TEXT;
        prepareAndSendMessage(chatId, answer);
    }
}
