package bitcamp.project4.context;

import bitcamp.project4.menu.MenuGroup;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

  MenuGroup mainMenu = new MenuGroup("행 거 맨");

  Map<String, Object> objContainer = new HashMap<>();

  public MenuGroup getMainMenu() {
    return mainMenu;
  }

  public void setAttribute(String name, Object value) {
    objContainer.put(name, value);
  }

  public Object getAttribute(String name) {
    return objContainer.get(name);
  }


}
