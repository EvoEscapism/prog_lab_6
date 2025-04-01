package client.commands;

import client.network.UDPClient;
import client.utility.console.Console;
import common.exceptions.*;
import common.network.requests.*;
import common.network.responses.*;

import java.io.IOException;

/**
 * Команда 'filter_by_price'. Фильтрация продуктов по цене.
 */
public class FilterByPrice extends Command {
  private final Console console;
  private final UDPClient client;

  public FilterByPrice(Console console, UDPClient client) {
    super("filter_by_score <PRICE>");
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

      var price = Long.parseLong(arguments[1]);
      var response = (FilterByPriceResponse) client.sendAndReceiveCommand(new FilterByPriceRequest(price));
      if (response.getError() != null && !response.getError().isEmpty()) {
        throw new APIException(response.getError());
      }

      if (response.filteredProducts.isEmpty()) {
        console.println("Bands with score " + price + " are not found.");
        return true;
      }

      console.println("Bands with score " + price + ": " + response.filteredProducts.size() + " .\n");
      response.filteredProducts.forEach(console::println);

      return true;
    } catch (WrongAmountOfElementsException exception) {
      console.printError("Wrong amount of arguments!");
      console.println("Using: '" + getName() + "'");
    } catch(IOException e) {
      console.printError("Band fields are invalid! Band is not created!");
    } catch (NumberFormatException exception) {
      console.printError("Server interaction error");
    } catch (APIException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
