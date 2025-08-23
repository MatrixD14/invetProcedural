public class test extends Component {
  public SpatialObject s;
  public ObjectFile[] f = new ObjectFile[5];

  void repeat() {
    if (Input.isKeyDown("invent") || Input.keyboard.isKeyDown("r")) {
      SpatialObject Object = myObject.Instantiate(f[Random.range(0, f.length - 1)], new Vector3(range(s.getScale().x), -0.5f, range(s.getScale().z)));
      item value = Object.findComponent("item");
      if(value == null) return;
      if(Object.getName() == "galho")value.logica = new arma(new espada());
      if(Object.getName() == "troco")value.logica = new arma(new espada());
   } 
  }

  private float range(float value) {
    return Random.range(-value / 10, value / 10);
  }
}
