public class phisics extends Component {
  private float gravity = 5f;
  private Vector3 velocity;
  private float ColliderSize = 1f;
  private float eixoX, eixoZ;

  void start() {
    velocity = new Vector3();
  }

  void repeat() {
    Vector3 pos = myObject.position;
    float delta = Time.deltatime();

    velocity.y = velocity.y + (-gravity * delta);
    pos.y = pos.y + velocity.y * delta;

    float HeightMax = 1f, stepSpeed = 5f;
    float veloDX = velocity.x * delta, veloDZ = velocity.z * delta;

    float blockInic = getBlock(pos.x, pos.z);
    float diffX = getBlock(pos.x + veloDX, pos.z) - blockInic;
    float diffZ = getBlock(pos.x, pos.z + veloDZ) - blockInic;

    int veloX = 1, veloZ = 1;
    if (Math.abs(diffX) > HeightMax) veloX = 0;
    if (Math.abs(diffZ) > HeightMax) veloZ = 0;

    float HeightDistY = blockInic + ColliderSize;
    float diffY = HeightDistY - pos.y;

    if (veloZ != 0 && veloX != 0) {
      if (diffY > 0 && diffY <= HeightMax) {
        pos.y = Math.min(pos.y + stepSpeed * delta, HeightDistY);
        velocity.y = 0;
      } else if (pos.y < HeightDistY) {
        pos.y = HeightDistY;
        velocity.y = 0;
      }
    }
    eixoX = veloX;
    eixoZ = veloZ;
  } 

  public float moveX() {
    return eixoX;
  }

  public float moveZ() {
    return eixoZ;
  }

  public float getBlock(float x, float z) {
    TerreController terreno = getTerreno(x, z);
    if (terreno == null) return 0f;
    return terreno.getHeight(x, z);
  }

  public TerreController getTerreno(float x, float z) {
    chunkgen chunck = (chunkgen) myObject.findComponent("chunkgen");
    if (chunck == null) return null;
    int coodX = (int) Math.floor(x / chunck.width);
    int coodZ = (int) Math.floor(z / chunck.width);
    long codekey = chunck.CodificKey(coodX, coodZ);
    SpatialObject ObjChunck = chunck.chunck.get(codekey);
    if (ObjChunck == null || !ObjChunck.exists()) return null;
    return (TerreController) ObjChunck.findComponent("TerreController");
  }
}
