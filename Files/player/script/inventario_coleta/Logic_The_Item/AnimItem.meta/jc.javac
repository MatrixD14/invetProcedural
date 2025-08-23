public class AnimItem extends Component {
  private boolean onoff = false;
  private SpatialObject button;
  public int value = 0;

  void start() {
    button = WorldController.findObject("AcaoItem");
  }

  void repeat() {
    Quaternion myrot = myObject.rotation;
    if (Input.isKeyDown("acaoitem")) onoff = !onoff;
    if (value <= 75 && getOnOff()) value += 25;
    else if (value >= 0) {
      value -= 25;
      onoff = false;
    } 
    myrot.x = value;
  }

  public boolean getOnOff() {
    return onoff;
  }

  public void setButton(boolean tf) {
    button.setEnabled(tf);
  }
}
