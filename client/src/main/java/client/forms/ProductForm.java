package client.forms;

import client.utility.console.Console;
import client.utility.Interrogator;
import common.domain.*;
import common.exceptions.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * Форма продукта.

 */
public class ProductForm extends Form<Product> {
  private final Console console;

  public ProductForm(Console console) {
    this.console = console;
  }

  @Override
  public Product build() throws IncorrectInputInScriptException, InvalidFormException {
    var product = new Product(
      1,
      askName(),
      askCoordinates(),
      LocalDate.now(),
      askPrice(),
      askPartNumber(),
      askUnitOfMeasureType(),
      askOrganization()
    );
    if (!product.validate()) throw new InvalidFormException();
    return product;
  }

  private String askName() throws IncorrectInputInScriptException {
    String name;
    var fileMode = Interrogator.fileMode();
    while (true) {
      try {
        console.println("Enter band name:");
        console.ps2();

        name = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(name);
        if (name.equals("")) throw new MustBeNotEmptyException();
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Name is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (MustBeNotEmptyException exception) {
        console.printError("Name cannot be empty!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }

    return name;
  }

  private Coordinates askCoordinates() throws IncorrectInputInScriptException, InvalidFormException {
    return new CoordinatesForm(console).build();
  }

  private Long askPrice() throws IncorrectInputInScriptException {
    var fileMode = Interrogator.fileMode();
    long price;
    while (true) {
      try {
        console.println("Enter band`s score:");
        console.ps2();

        var strPrice = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(strPrice);

        price = Long.parseLong(strPrice);
        long MIN_PRICE = 0;
        if (price < MIN_PRICE) throw new NotInDeclaredLimitsException();
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Bands score is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NotInDeclaredLimitsException exception) {
        console.printError("Band`s score must be more than 0!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NumberFormatException exception) {
        console.printError("Band`s score must be number");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NullPointerException | IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }
    return price;
  }

  private String askPartNumber() throws IncorrectInputInScriptException {
    String partNumber;
    var fileMode = Interrogator.fileMode();
    while (true) {
      try {
        console.println("Enter amount of participants:");
        console.ps2();

        partNumber = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(partNumber);
        if (partNumber.equals("")) {
          partNumber = null;
        }
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Amount of participants is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }

    return partNumber;
  }

  private UnitOfMeasure askUnitOfMeasureType() throws IncorrectInputInScriptException {
    return new UnitOfMeasureForm(console).build();
  }

  private Organization askOrganization() throws IncorrectInputInScriptException, InvalidFormException {
    return new OrganizationForm(console).build();
  }
}
