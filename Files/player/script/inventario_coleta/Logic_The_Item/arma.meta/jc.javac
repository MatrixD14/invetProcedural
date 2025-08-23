public class arma extends atributo {
  public interface AcaoItem {
      void execute(item arm);
  }
  private AnimItem anim;
  private AcaoItem acao;

  public arma(AcaoItem acao) {
    this.acao = acao;
  }
  void start(){
      anim = WorldController.findObject("object").findComponent("AnimItem");
  }
  public void Used(item obj) {
      if(anim == null)start();
      anim.setButton(true);
      if(anim.getOnOff())acao.execute(obj);
  }

  public void NoUsed(item obj) {
      if(anim == null)start();
      anim.setButton(false);
  }
} 
