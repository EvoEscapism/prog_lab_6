package server.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import common.domain.Product;
import server.App;
import server.utility.LocalDateAdapter;

/**
 * Использует файл для сохранения и загрузки коллекции.

 */
public class DumpManager {
  private final Gson gson = new GsonBuilder()
    .setPrettyPrinting()
    .serializeNulls()
    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    .create();

  private final String fileName;

  public DumpManager(String fileName) {
    if (!(new File(fileName).exists()))  fileName = "../" + fileName;
    this.fileName = fileName;
  }

  /**
   * Записывает коллекцию в файл.
   * @param collection коллекция
   */
  public void writeCollection(Collection<Product> collection) {
    try (PrintWriter collectionPrintWriter = new PrintWriter(new File(fileName))) {
      collectionPrintWriter.println(gson.toJson(collection));
      App.logger.info("\n" + "Collection successfully saved to file!");
    } catch (IOException exception) {
      App.logger.error("\n" + "The download file could not be opened.!");
    }
  }

  /**
   * Считывает коллекцию из файла.
   * @return Считанная коллекция
   */
  public Collection<Product> readCollection() {
    if (fileName != null && !fileName.isEmpty()) {
      try (var fileReader = new FileReader(fileName)) {
        var collectionType = new TypeToken<PriorityQueue<Product>>() {}.getType();
        var reader = new BufferedReader(fileReader);

        var jsonString = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null) {
          line = line.trim();
          if (!line.equals("")) {
            jsonString.append(line);
          }
        }

        if (jsonString.isEmpty()) {
          jsonString = new StringBuilder("[]");
        }

        PriorityQueue<Product> collection = gson.fromJson(jsonString.toString(), collectionType);

        App.logger.info("Collection successfully loaded!");
        return collection;

      } catch (FileNotFoundException exception) {
        App.logger.error("\n" + "Boot file not found!");
      } catch (NoSuchElementException exception) {
        App.logger.error("The boot file is empty\n!");
      } catch (JsonParseException exception) {
        App.logger.error("The required collection was not found in the boot file\n!");
      } catch (IllegalStateException | IOException exception) {
        App.logger.fatal("\n" + "Unexpected error!");
        System.exit(0);
      }
    } else {
      App.logger.error("\n" + "Command line argument with boot file not found!");
    }
    return new PriorityQueue<>();
  }
}
