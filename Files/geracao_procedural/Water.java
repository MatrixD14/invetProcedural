public class Water extends Component {
  private ModelRenderer WaterModelo;
  private Vertex WaterVertex;
  private Vector2Buffer WaterUV = null;
  private Vector3Buffer WaterVertices = null, WaterNormal = null;
  private IntBuffer WaterTriangle = null;
  private chunkgen tama;
  private malha modela = new malha();

  void start() {
    if (!myObject.exists()) return;
    tama = WorldController.findObject("player").findComponent("chunkgen");
    WaterModelo = myObject.findComponent("modelrenderer");
    WaterVertex = new Vertex();
   // if (myObject.exists()) return;
    // WaterVertices.clear();
    // WaterNormal.clear();
    //WaterUV.clear();
  }

  public void WaterGera() {
    start();
    createBuffer();
    for (int z = 0; z <= 4; z++) {
      for (int x = 0; x <= 4; x++) {
        WaterVertices.put(x * 4, tama.waterlevel, z * 4);
        WaterNormal.put(0, 1, 0);
        WaterUV.put(new Vector2(x, z));
      }
    }
    modela.trianguloN(4, WaterTriangle);
    int[] WaterTriangleConvert = new int[WaterTriangle.position()];
    WaterTriangle.rewind();
    WaterTriangle.get(WaterTriangleConvert);
    WaterVertex = modela.meshupN(false, WaterModelo, tama.WaterMate, WaterTriangleConvert, WaterVertices, WaterNormal, WaterUV);
    myObject.removeComponent(new Water());
  }

  public void createBuffer() {
    chunkSimul data = new chunkSimul();
    data.generatWater(4, modela);
    WaterVertices = BufferUtils.createVector3Buffer(data.VertecesWaterCount);
    WaterNormal = BufferUtils.createVector3Buffer(data.NormalWaterCount);
    WaterTriangle = BufferUtils.createIntBuffer(data.TrianWaterCount);
    WaterUV = BufferUtils.createVector2Buffer(data.UvMapWaterCount);
    WaterVertices.setVboEnabled(true);
    WaterNormal.setVboEnabled(true);
    WaterUV.setVboEnabled(true);
  } 
}