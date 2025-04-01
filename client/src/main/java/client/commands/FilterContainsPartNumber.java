package client.commands;

import client.network.UDPClient;
import client.utility.console.Console;
import common.exceptions.*;
import common.network.requests.*;
import common.network.responses.*;

import java.io.IOException;

/**
 * Команда 'filter_contains_part_number'. Фильтрация продуктов по полю partNumber.
 */
public class FilterContainsPartNumber extends Command {
  private final Console console;
  private final UDPClient client;

  public FilterContainsPartNumber(Console console, UDPClient client) {
    super("filter_contains_part_number <PN>");
    this.console = console;
    this.client = client;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    try {
      if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

      var response = (FilterContainsPartNumberResponse) client.sendAndReceiveCommand(
        new FilterContainsPartNumberRequest(arguments[1])
      );
      if (response.getError() != null && !response.getError().isEmpty()) {
        throw new APIException(response.getError());
      }

      if (response.filteredProducts.isEmpty()) {
        console.println("Band with partNumber contains '" + arguments[1] + "' are not found.");
        return true;
      }

      console.println("Band with partNumber contains '" + arguments[1] + "' found " + response.filteredProducts.size() + " .\n");
      response.filteredProducts.forEach(console::println);

      return true;
    } catch (WrongAmountOfElementsException exception) {
      console.printError("Wrong amount of arguments");
      console.println("Using: '" + getName() + "'");
    } catch(IOException e) {
      console.printError("Server interaction error");
    } catch (APIException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
