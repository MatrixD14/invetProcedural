public class chunkSimul {
  public int VertecesCount, NormalCount, TrianCount, UvMapCount;
  public int VertecesWaterCount, NormalWaterCount, TrianWaterCount, UvMapWaterCount;

  public void generat(int value, OH2LevelIntArray block, OH2LevelFloatArray heigth, malha data) {
    for (int z = 0; z <= value; z++) {
      for (int x = 0; x <= value; x++) {
        float y = heigth.get(z, x);
        int matriz = block.get(z, x);
        data.ChunckSimul(this);
      }
    } 
  }

  public void generatWater(int value, malha data) {
    for (int z = 0; z <= value; z++) {
      for (int x = 0; x <= value; x++) {
        data.WaterSimul(this);
      }
    }
  }
}
