public class phisics extends Component {
  private float gravity = 5f;
  private Vector3 velocity;
  private float ColliderSize = 1f;
  private float eixoX, eixoZ;

  private TerreController correntChunck = null;
  private int correntX = Integer.MIN_VALUE;
  private int correntZ = Integer.MIN_VALUE;

  void start() {
    ;
    velocity = new Vector3();
  }

  void repeat() {
    Vector3 pos = myObject.position;
    float delta = Time.deltatime();

    velocity.y = velocity.y + (-gravity * delta);
    pos.y = pos.y + velocity.y * delta;

    float HeightMax = 1.5f;
    float veloDX = velocity.x * delta, veloDZ = velocity.z * delta;
    
    getUpTerreno(pos.x,pos.z);
    float blockInic = getFormBlock(pos.x, pos.z);
    float blockZ = getFormBlock(pos.x, pos.z + veloDZ);
    float blockX = getFormBlock(pos.x + veloDX, pos.z);
    
    eixoX = Math.abs(blockZ - blockInic) > HeightMax ? 0 : 1f;
    eixoZ = Math.abs(blockX - blockInic) > HeightMax ? 0 : 1f;

    float HeightDistY = blockInic + ColliderSize;
    float diffY = HeightDistY - pos.y;

    if (eixoX != 0 && eixoZ != 0) {
      float stepSpeed = 5f;
      if (diffY > 0 && diffY <= HeightMax) {
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

  public float getFormBlock(float x, float z) {
    return (correntChunck != null) ? correntChunck.getHeight(x, z) : 0f;
  }

  public void getUpTerreno(float x, float z) {
    chunkgen chunck = (chunkgen) myObject.findComponent("chunkgen");
    if (chunck == null) {
      correntChunck = null;
      return;
    }
    int coodX = (int) Math.floor(x / chunck.width);
    int coodZ = (int) Math.floor(z / chunck.width);
    if (coodX != correntX || coodZ != correntZ) {
      correntX = coodX;
      correntZ = coodZ;
      long codekey = chunck.CodificKey(coodX, coodZ);
      SpatialObject ObjChunck = chunck.chunck.get(codekey);
      if (ObjChunck != null && ObjChunck.exists()) correntChunck = (TerreController) ObjChunck.findComponent("TerreController");
      else correntChunck = null;
    } 
  }
}
