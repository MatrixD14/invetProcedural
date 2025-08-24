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

    float HeightMax = 1.5f;
    float veloDX = velocity.x * delta, veloDZ = velocity.z * delta;

    float blockInic = getBlock(pos.x, pos.z);
    float blockX = getBlock(pos.x, pos.z + veloDZ);
    float blockZ = getBlock(pos.x + veloDX, pos.z);
    
    eixoX = Math.abs(blockZ - blockInic) > HeightMax ? 0 : 1f;
    eixoZ = Math.abs(blockX - blockInic) > HeightMax ? 0 : 1f;
    
    float HeightDistY = blockInic + ColliderSize;
    float diffY = HeightDistY - pos.y;

    if (eixoX != 0 && eixoZ != 0) {
      if (diffY > 0 && diffY <= HeightMax) {
        float stepSpeed = 5f;
        pos.y = Math.min(pos.y + stepSpeed * delta, HeightDistY);
        velocity.y = 0;
      } else if (pos.y < HeightDistY) {
        pos.y = HeightDistY;
        velocity.y = 0;
      } 
    }
  }

  public float moveX() {
    return eixoX;
  }

  public float moveZ() {
    return eixoZ;
  }

  public float getBlock(float x, float z) {
    TerreController terreno = getTerreno(x, z);
    return (terreno != null) ? terreno.getHeight(x, z) : 0f;
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