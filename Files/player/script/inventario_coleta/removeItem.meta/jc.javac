public class removeItem {
  private int values = 1;

  public void CaractItem(item rmItem, int value, SpatialObject objitem, ObjectFile drops) {
    if (rmItem == null || value <= 0) return;
    Vector3 mypos = objitem.getGlobalPosition();
    SpatialObject drop = objitem.Instantiate(drops, mypos);
    drop.setName(rmItem.name);
    drop.setStatic(false);
    drop.getPhysics().setPhysicsEntity(new Rigidbody());
    if (drop.findComponent("item") == null) drop.addComponent(new item());
    item addDados = drop.findComponent(item.class);

    if (drop.findComponent("Collider") == null) drop.addComponent(new Collider());
    Collider ItemCollider = drop.findComponent("collider");

    if (drop.findComponent("ModelRenderer") == null) drop.addComponent(new ModelRenderer());
    ModelRenderer ItemType = drop.findComponent("ModelRenderer");

    if (addDados != null) {
      DadosItem(addDados, rmItem);
      addDados.QuatItemGrup = value;
      values = 1;
    }
    if (ItemType != null) ItemType.setModelFile(rmItem.vertex);
    if (ItemCollider != null) {
      ItemCollider.setShape(4);
      ItemCollider.setVertexFile(rmItem.vertex);
    }
  }

  public int valueDele() {
    SUIText text = null;
    if (text == null) text = WorldController.findObject("defQuatDelet").findChildObject("QuantD").findComponent("SUIText");
    if (Input.isKeyDown("mais")) values++;
    if (Input.isKeyDown("menos") && values > 1) values--;
    if (text != null) text.setText("" + values);
    return values;
  } 

  private void DadosItem(item armazena, item dados) {
    armazena.name = dados.name;
    armazena.id = dados.id;
    armazena.ui = dados.ui;
    armazena.vertex = dados.vertex;
    armazena.value = dados.value;
    armazena.typeDC = dados.typeDC;
    armazena.maxgrup = dados.maxgrup;
    armazena.MapSpriteX = dados.MapSpriteX;
    armazena.MapSpriteY = dados.MapSpriteY;
    armazena.logica = dados.logica;
  }
}
