package server.commands;

import common.network.requests.Request;
import common.network.responses.*;
import server.repositories.ProductRepository;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
  private final ProductRepository productRepository;

  public Info(ProductRepository productRepository) {
    super("info", "Display info of Collection");
    this.productRepository = productRepository;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public Response apply(Request request) {
    var lastInitTime = productRepository.getLastInitTime();
    var lastInitTimeString = (lastInitTime == null) ? "Initialization has not yet occurred in this session" :
      lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

    var lastSaveTime = productRepository.getLastSaveTime();
    var lastSaveTimeString = (lastSaveTime == null) ? "no saves have occurred yet in this session" :
      lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

    var message = "Collection info:\n" +
      " Type: " + productRepository.type() + "\n" +
      " Amount of elements: " + productRepository.size() + "\n" +
      "Date last saved: " + lastSaveTimeString + "\n" +
      "Last initialization date: " + lastInitTimeString;
    return new InfoResponse(message, null);
  }
}
