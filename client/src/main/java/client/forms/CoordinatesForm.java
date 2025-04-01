package client.forms;

import client.utility.console.Console;
import client.utility.Interrogator;
import common.domain.Coordinates;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.InvalidFormException;

import java.util.NoSuchElementException;

public class CoordinatesForm extends Form<Coordinates> {
  private final Console console;

  public CoordinatesForm(Console console) {
    this.console = console;
  }

  @Override
  public Coordinates build() throws IncorrectInputInScriptException, InvalidFormException {
    var coordinates = new Coordinates(askX(), askY());
    if (!coordinates.validate()) throw new InvalidFormException();
    return coordinates;
  }

  /**
   * Запрашивает у пользователя координату X.
   * @return Координата X.
   * @throws IncorrectInputInScriptException Если запущен скрипт и возникает ошибка.
   */
  public Integer askX() throws IncorrectInputInScriptException {
    var fileMode = Interrogator.fileMode();
    int x;
    while (true) {
      try {
        console.println("Enter coordinate X:");
        console.ps2();
        var strX = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(strX);

        x = Integer.parseInt(strX);
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Coordinate X is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NumberFormatException exception) {
        console.printError("Coordinate X must be a number!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NullPointerException | IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }
    return x;
  }

  /**
   * Запрашивает у пользователя координату Y.
   * @return Координата Y.
   * @throws IncorrectInputInScriptException Если запущен скрипт и возникает ошибка.
   */
  public Long askY() throws IncorrectInputInScriptException {
    var fileMode = Interrogator.fileMode();
    long y;
    while (true) {
      try {
        console.println("Enter coordinate Y:");
        console.ps2();
        var strY = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(strY);

        y = Long.parseLong(strY);
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Coordinate Y is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NumberFormatException exception) {
        console.printError("Coordinate Y must be a number!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NullPointerException | IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }
    return y;
  }
}
