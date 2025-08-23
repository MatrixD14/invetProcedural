public class weatheffect extends Component {
  public SpatialObject item;
  private float lerpPos = 20f, lerpRot = 20f;

  void repeat() {
    Vector3 myPos = myObject.globalPosition;
    Quaternion myRot = myObject.globalRotation;
    myPos.blend(item.globalPosition, Math.bySecond(lerpPos));
    myRot.blend(item.globalRotation, Math.bySecond(lerpRot));
    
    myObject.position = myPos;
    myObject.rotation = myRot;
 } 
}
