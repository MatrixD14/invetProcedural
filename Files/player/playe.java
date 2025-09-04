public class playe extends Component {
  private Vector2 joy = null, slid = null, slidmouse = null;
  private float speedJ = 5f, camx, camy, camrota = 1f;
  public WorldFile world, menu;
  private boolean playstop = false;
  private onoffinvent open;
  private phisics fisica;

  void start() {
    open = WorldController.findObject("player").findComponent("onoffinvent");
    fisica = myObject.findComponent("phisics");
    joy = Input.getAxisValue("joy");
    slid = Input.getAxisValue("slid");
    slidmouse = new Vector2(Input.mouse.getSlideX(), Input.mouse.getSlideY());
  }

  void repeat() {
    if (!playstop) GUI.drawText("FPS: " + (int) (1 / Time.deltatime()),(Screen.width()/2)+400,(Screen.height()/2)-350,30,12,8);
    if (open.onoff) {
      move(0, 0);
      return;
    }
    sairgame();
    mudamudo();
    if (playstop) return;
    if (key("w") || key("s") || key("a") || key("d")) {
      movekey();
    } else move(joy.x * speedJ, joy.y * speedJ);
    cameMouse(slid);
  }

  private void cameMouse(Vector2 slide) {
    camx += slide.x * camrota;
    camy = Math.clamp(-90, camy += slide.y * camrota, 90);
    myObject.getRotation().selfLookTo(new Vector3(Math.sin(-camx), 0, Math.cos(-camx)));
    myObject.findChildObject("see_player").getRotation().selfLookTo(new Vector3(0, Math.sin(-camy), Math.cos(-camy)));
  }

  private void movekey() {
    float x = 0, y = 0;
    if (key("w")) y = +.5f;
    if (key("s")) y = -.5f;
    if (key("a")) x = -.5f;
    if (key("d")) x = +.5f;
    move(x * speedJ, y * speedJ);
  }

  private boolean key(String key) {
    if (Input.keyboard.isKeyPressed(key)) return true;
    return false;
  } 

  private void move(float x, float y) {
    myObject.moveInSeconds(-(x * fisica.moveX()), 0, y * fisica.moveZ());
  }

  private void sairgame() {
    if (Input.isKeyDown("sair")) GameController.quit();
    if (Input.isKeyDown("playStop")) playstop = !playstop;
    /*if (playstop) {
      Time.setTimeScale(0);
    } else Time.setTimeScale(1);*/
  }

  private void mudamudo() {
    if (Input.isKeyDown("reload")) WorldController.loadWorld(world);
    // if (Input.isKeyDown("menu")) WorldController.loadWorld(menu);
  }
}
