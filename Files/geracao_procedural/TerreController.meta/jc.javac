public class TerreController extends Component {
  private ModelRenderer TerrModelo;
  private Vertex TerrVertex;
  private Vector3Buffer TerrVertices = null, TerrNormal = null;
  private IntBuffer TerrTriangles = null;
  private Vector2Buffer TerrUVs = null;
  private chunkgen tama;
  private Vector3 mypos;
  private HashMap<Long, Float> HeightMap = new HashMap<Long, Float>();
  private OH2LevelIntArray block = null;
  private OH2LevelFloatArray heigth = null;
  private malha modela = new malha();

  void start() {
    if (!myObject.exists()) return;
    tama = WorldController.findObject("player").findComponent("chunkgen");
    TerrModelo = myObject.findComponent("modelrenderer");

    reload();
  } 

  private void myposblock() {
    mypos = myObject.position;
  }

  public void reload() {
    new AsyncTask(
        new AsyncRunnable() {
          public Object onBackground(Object input) {
            TerrVertex = new Vertex();
            myposblock();
            MatrizChunck();
            createBuffer();
            HeightMap.clear();
            return null;
          }

          public void onEngine(Object result) {
            generat();
          }
        });
  }

  private void MatrizChunck() {
    int W = tama.width;
    block = new OH2LevelIntArray(W + 1, W + 1);
    heigth = new OH2LevelFloatArray(W + 1, W + 1);
    for (int z = 0; z <= W; z++) {
      for (int x = 0; x <= W; x++) {
        float yLocal = modela.perlinnoises(tama, myObject, x, z);
        float yWorld = yLocal + mypos.y;
        heigth.set(z, x, yLocal);
        if (yWorld >= tama.waterlevel - 2 && yWorld <= 2 + tama.waterlevel) block.set(z, x, 0);
        else if (yWorld >= tama.waterlevel - 15 && yWorld <= tama.waterlevel - 2) block.set(z, x, 12);
        else if (yWorld <= tama.waterlevel - 15) block.set(z, x, 7);
        else block.set(z, x, 1);
      }
    }
  }

  private void createBuffer() {
    chunkSimul data = new chunkSimul();
    boolean offon = true;
    data.generat(tama.width, block, heigth, modela);
    TerrVertices = BufferUtils.createVector3Buffer(data.VertecesCount);
    TerrNormal = BufferUtils.createVector3Buffer(data.NormalCount);
    TerrTriangles = BufferUtils.createIntBuffer(data.TrianCount);
    TerrUVs = BufferUtils.createVector2Buffer(data.UvMapCount);
    if (!offon) return;
    TerrVertices.setVboEnabled(true);
    TerrNormal.setVboEnabled(true);
    TerrUVs.setVboEnabled(true);
  }

  private void generat() {
    int W = tama.width;
    int para = 0;
    for (int z = 0; z <= W; z++) {
      for (int x = 0; x <= W; x++) {
        int matriz = block.get(z, x);
        float y = heigth.get(z, x);
        long codekey = CodificKey((int) (x + mypos.x), (int) (z + mypos.z));
        HeightMap.put(codekey, y + mypos.y);
        topFace(x, y, z, matriz);
        modela.generationlog(tama, mypos, myObject, x, y, z);
        if (matriz != 1) para = 1;
      }
    }
    if (para == 1) WaterCriate();

    modela.trianguloN(W, TerrTriangles);
    int[] TrianConvert = new int[TerrTriangles.position()];
    TerrTriangles.rewind();
    TerrTriangles.get(TrianConvert);
    TerrVertex = modela.meshupN(false, TerrModelo, tama.TerrMate, TrianConvert, TerrVertices, TerrNormal, TerrUVs);
  }

  private void topFace(int x, float y, int z, int typeblock) {
    if (typeblock < 0) return;
    TerrVertices.put(x, y, z);
    TerrNormal.put(0, 1, 0);
    TerrUVs.put(mapuv(typeblock, 0, 0));
  }

  private Vector2 mapuv(int type, int x, int y) {
    int t = 4;
    float tilasize = 1f / t;
    int tx = type % t;
    int ty = type / t;
    float u = tx * tilasize + x * tilasize, v = ty * tilasize + y * tilasize;
    return new Vector2(u, v);
  }

  public void WaterCriate() {
    SpatialObject Obj = new SpatialObject("Water");
    Obj.setParent(myObject);
    Obj.setStatic(true);
    Obj.addComponent(new ModelRenderer());
    Obj.addComponent(new Water());
    Water gera = null;
    if (Obj.findComponent("water") != null) gera = Obj.findComponent("Water");
    if (gera != null) gera.WaterGera();
  }

  public float getHeight(float x, float z) {
    long codeKey = CodificKey((int) Math.floor(x), (int) Math.floor(z));
    return HeightMap.getOrDefault(codeKey, 0f);
  }

  public long CodificKey(int x, int z) {
    return (((long) x) << 32) | (z & 0xFFFFFFFFL);
  }
}
