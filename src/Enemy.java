import org.openpatch.scratch.Sprite;

public class Enemy  extends Sprite {

    protected String name;
    protected String path;
    protected double speed;
    protected double exp;
    protected int frameDelay;
    protected  int hp;
    protected  int frame;

    Enemy(){
        path = "sprites/Enemy/";
    }
    @Override
    public void run() {
        // 일시정지 아닐 때만 실행 (GameManager에서 확인)
        if (!GameManager.Instance.isGamePaused) {

            Move(); // 이동
            Animation();   // 애니메이션
            Damage();  // 피격 판정 (Damage)
        }
    }
    protected void Move() {}
    protected void Animation() {}
    protected void Damage() {}
    private  void Die(){}
    public void setPosition() {
        this.setX((Math.random() * 600) - 200); // 오른쪽 끝
        this.setY((Math.random() * 600) - 200); // 위아래 랜덤 위치
    }



}
