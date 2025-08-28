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
  }

  public void WaterGera() {
    start();
    createBuffer();
    int mult = 1 * 16;
    geraVerteces(mult, mult);
    geraVerteces(0, mult);
    geraVerteces(mult, 0);
    geraVerteces(0, 0);
    
    modela.trianguloN(1, WaterTriangle);
    int[] WaterTriangleConvert = new int[WaterTriangle.position()];
    WaterTriangle.rewind();
    WaterTriangle.get(WaterTriangleConvert);
    WaterVertex = modela.meshupN(false, WaterModelo, tama.WaterMate, WaterTriangleConvert, WaterVertices, WaterNormal, WaterUV);
    myObject.removeComponent(new Water());
  }

  private void geraVerteces(int x, int z) {
    WaterVertices.put(x, tama.waterlevel, z);
    WaterNormal.put(0, 1, 0);
    WaterUV.put(new Vector2(x, z));
  }

  public void createBuffer() {
    chunkSimul data = new chunkSimul();
    boolean offon = false;
    data.generatWater(1, modela);
    WaterVertices = BufferUtils.createVector3Buffer(data.VertecesWaterCount);
    WaterNormal = BufferUtils.createVector3Buffer(data.NormalWaterCount);
    WaterTriangle = BufferUtils.createIntBuffer(data.TrianWaterCount);
    WaterUV = BufferUtils.createVector2Buffer(data.UvMapWaterCount);
    if (!offon) return;
    WaterVertices.setVboEnabled(true);
    WaterNormal.setVboEnabled(true);
    WaterUV.setVboEnabled(true);
  }
}
