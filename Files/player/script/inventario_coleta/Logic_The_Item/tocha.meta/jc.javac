public class tocha extends atributo {
  private SpatialObject mao;
  private Light luz;
  private Color fogo = new Color(255, 200, 100);

  void start() {
    mao = WorldController.findObject("itemMust").findChildObject("object");
    if (mao.findComponent("light") == null) mao.addComponent(new Light(1));
    if (luz == null) luz = mao.findComponent("Light");
    luz.setDiameter(15);
  }

  public void Used(item obj) {
    start();
    if (luz != null) {
      luz.setIntensity(2f);
      luz.setColor(fogo);
    } 
  }

  public void NoUsed(item obj) {
    start();
    if (luz != null) luz.setIntensity(0f);
  }
}
