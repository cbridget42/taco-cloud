package com.sia.taco.cloud.client.ui;

import com.sia.taco.cloud.api.dto.IngredientDto;
import com.sia.taco.cloud.client.client.RestTacoClient;
import com.sia.taco.cloud.client.parser.CommonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleUI implements CommandLineRunner {

    private final RestTacoClient tacoClient;
    private final CommonParser commonParser;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) break;

            processCommand(input);
        }
    }

    private void processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];

        try {
            switch (command) {
                case "get":
                    var found = tacoClient.getIngredientById(parts[1]);
                    System.out.printf("found: %s%n", found);
                    break;
                case "create":
                    var created = tacoClient.createIngredient(parseIngredient(parts[1]));
                    System.out.printf("created: %s%n", created);
                    break;
                case "delete":
                    tacoClient.deleteIngredient(parts[1]);
                    break;
                default:
                    System.out.println("Unknown command");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private IngredientDto parseIngredient(String part) {
        return commonParser.parse(IngredientDto.class, part);
    }
}
