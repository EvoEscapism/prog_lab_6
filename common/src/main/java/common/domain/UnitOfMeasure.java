package common.domain;

import java.io.Serializable;

/**
 * Перечисление типов единиц измерения продукта.
 * @author maxbarsukov
 */
public enum UnitOfMeasure implements Serializable {
  ROCK,
  TRAP,
  POP,
  SOUL;

  /**
   * @return Строка со всеми элементами enum'а через строку.
   */
  public static String names() {
    StringBuilder nameList = new StringBuilder();
    for (var weaponType : values()) {
      nameList.append(weaponType.name()).append(", ");
    }
    return nameList.substring(0, nameList.length()-2);
  }
}
