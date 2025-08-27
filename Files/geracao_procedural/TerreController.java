public class TerreController extends Component {
  private SpatialObject armLog, Obj, voi;
  private ModelRenderer TerrModelo;
  private Vertex TerrVertex;
  private Vector3Buffer TerrVertices = null, TerrNormal = null;
  private IntBuffer TerrTriangles = null;
  private Vector2Buffer TerrUVs = null;
  private PerlinNoise perlin = new PerlinNoise(100);
  private chunkgen tama;
  private Vector3 mypos;
  private HashMap<Long, Float> HeightMap = new HashMap<Long, Float>();
  private int[][] block = null;
  private float[][] heigth = null;
  private malha modela = new malha();

  void start() {
    if (!myObject.exists()) return;
    tama = WorldController.findObject("player").findComponent("chunkgen");
    armLog = WorldController.findObject("log");
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
            // TerrUV.clear();
            return null;
          }

          public void onEngine(Object result) {
            WaterCriate();
            generat();
            if (Obj != null && Obj.exists()) {
              Water gera = Obj.findComponent("Water");
              if (gera != null) gera.WaterGera();
            } 
          }
        });
  }

  private void MatrizChunck() {
    int W = tama.width;
    block = new int[W + 1][W + 1];
    heigth = new float[W + 1][W + 1];
    for (int z = 0; z <= W; z++) {
      for (int x = 0; x <= W; x++) {
        float yLocal = modela.perlinnoises(tama, myObject, x, z);
        float yWorld = yLocal + mypos.y;
        heigth[z][x] = yLocal;
        if (yWorld >= tama.waterlevel - 2 && yWorld <= 2 + tama.waterlevel) block[z][x] = 0;
        else if (yWorld >= tama.waterlevel - 15 && yWorld <= tama.waterlevel - 2) block[z][x] = 12;
        else if (yWorld <= tama.waterlevel - 15) block[z][x] = 7;
        else block[z][x] = 1;
      }
    }
  }

  private void createBuffer() {
    chunkSimul data = new chunkSimul();
    data.generat(tama.width, block, heigth, modela);
    TerrVertices = BufferUtils.createVector3Buffer(data.VertecesCount);
    TerrNormal = BufferUtils.createVector3Buffer(data.NormalCount);
    TerrTriangles = BufferUtils.createIntBuffer(data.TrianCount);
    TerrUVs = BufferUtils.createVector2Buffer(data.UvMapCount);
    TerrVertices.setVboEnabled(true);
    TerrNormal.setVboEnabled(true);
    TerrUVs.setVboEnabled(true);
  }

  private void generat() {
    int W = tama.width;
    for (int z = 0; z <= W; z++) {
      for (int x = 0; x <= W; x++) {
        int matriz = block[z][x];
        float y = heigth[z][x];
        long codekey = CodificKey((int) (x + mypos.x), (int) (z + mypos.z));
        HeightMap.put(codekey, y + mypos.y);

        topFace(x, y, z, matriz);
        generationlog(x, y, z);
      }
    }
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
    Obj = new SpatialObject("Water");
    Obj.setParent(myObject);
    Obj.setStatic(true);
    Obj.addComponent(new ModelRenderer());
    Obj.addComponent(new Water());
  }

  private void generationlog(float x, float y, float z) {
    float worldx = x + mypos.x;
    float worldz = z + mypos.z;
    int chunkX = (int) myObject.getGlobalPosition().x;
    int chunkZ = (int) myObject.getGlobalPosition().z;
    if (worldx < chunkX || worldx >= chunkX + tama.width || worldx < chunkZ || worldz >= chunkZ + tama.width) return;
    int space = Random.range(3, 5);
    if (((int) worldx % space != 0) || ((int) worldz % space != 0)) return;
    float addspaw = perlin.noise(worldx + tama.seed, worldz + tama.seed);
    addspaw -= perlin.noise(worldx * 50f + tama.seed, worldz * 50f + tama.seed);
    if (addspaw >= tama.valuelog && y > tama.waterlevel + tama.heightscale) {
      int quemspaw = Random.range(0, tama.trees.size() - 1);
      Vector3 positobj = new Vector3(worldx, y + mypos.y, worldz) - myObject.getGlobalPosition();
      SpatialObject log = myObject.instantiate(tama.trees.get(quemspaw), positobj);
      log.setParent(myObject);
    }
  }

  public float getHeight(float x, float z) {
    long codeKey = CodificKey((int) Math.floor(x), (int) Math.floor(z));
    return HeightMap.getOrDefault(codeKey, 0f);
  }

  public long CodificKey(int x, int z) {
    return (((long) x) << 32) | (z & 0xFFFFFFFFL);
  }
}
