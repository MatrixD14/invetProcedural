public class malha {
  private PerlinNoise perlin = new PerlinNoise(100);

  public void ChunckSimul(chunkSimul data) {
    data.VertecesCount += 1;
    data.NormalCount += 1;
    data.TrianCount += 6;
    data.UvMapCount += 1;
  }

  public void WaterSimul(chunkSimul data) {
    data.VertecesWaterCount += 1;
    data.NormalWaterCount += 1;
    data.TrianWaterCount += 6;
    data.UvMapWaterCount += 1;
  } 

  public void trianguloN(int value, IntBuffer triang) {
    int verts = 0;
    for (int z = 0; z < value; z++) {
      for (int x = 0; x < value; x++) {
        triang.put(verts + 0);
        triang.put(verts + value + 1);
        triang.put(verts + 1);
        triang.put(verts + 1);
        triang.put(verts + value + 1);
        triang.put(verts + value + 2);
        verts++;
      }
      verts++;
    }
  }

  public Vertex meshupN(boolean onoff, ModelRenderer model, MaterialFile mateFile, int[] trianglo, Vector3Buffer vertices, Vector3Buffer normal, Vector2Buffer uv) {
    Vertex vertexs = new Vertex();
    vertexs.setVertices(vertices);
    vertexs.setNormals(normal);
    vertexs.setUVs(uv);
    vertexs.setTriangles(trianglo);
    vertexs.apply();

    model.setMaterialFile(mateFile);
    model.setVertex(vertexs);
    if (onoff) model.material.setReceiveLight(false);
    model.setCastShadowEnabled(false);
    return vertexs;
  }

  public float perlinnoises(chunkgen tama, SpatialObject myObj, float x, float z) {
    float calcu = 0;
    float pi = 22f / 7f;
    float valuex = (x + tama.seed) + myObj.position.x;
    float valuez = (z + tama.seed) + myObj.position.z;
    calcu += perlin.noise(valuex, valuez) * tama.heightscale;
    calcu += perlin.noise(valuex * pi, valuez * pi) * tama.heightscale * 0.5f;
    calcu += perlin.noise(valuex / pi, valuez / pi) * tama.heightscale / 0.5f;
    calcu += perlin.noise(valuex - pi, valuez - pi) * tama.heightscale - 0.1f;
    return calcu;
  }
}
