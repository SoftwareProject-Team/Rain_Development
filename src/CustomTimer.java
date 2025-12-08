import org.openpatch.scratch.internal.Applet;


public class CustomTimer {
    private int startMillisEvery = -1;
    private int startMillisFor = -1;
    private int startMillisAfter = -1;
    private int startMillisInterval = -1;
    private int currentInterval = 0;

    public void reset() {
        this.startMillisEvery = -1;
        this.startMillisFor = -1;
        this.startMillisAfter = -1;
        this.startMillisInterval = -1;
        this.currentInterval = 0;
    }

    public int millis() {
        return GameManager.Instance.millis;
    }

    public boolean everyMillis(int millis) {
        int nowMillis = millis();
        if (this.startMillisEvery < 0) {
            this.startMillisEvery = nowMillis;
        }

        if (nowMillis >= this.startMillisEvery + millis) {
            this.startMillisEvery = nowMillis;
            return true;
        } else {
            return false;
        }
    }

    public boolean forMillis(int millis) {
        int nowMillis = millis();
        if (this.startMillisFor < 0) {
            this.startMillisFor = nowMillis;
        }

        return nowMillis < this.startMillisFor + millis;
    }

    public boolean afterMillis(int millis) {
        int nowMillis = millis();
        if (this.startMillisAfter < 0) {
            this.startMillisAfter = nowMillis;
        }

        return nowMillis >= this.startMillisAfter + millis;
    }

    public boolean intervalMillis(int millis) {
        return this.intervalMillis(millis, millis, false);
    }

    public boolean intervalMillis(int millis, boolean skipFirst) {
        return this.intervalMillis(millis, millis, skipFirst);
    }

    public boolean intervalMillis(int millis1, int millis2) {
        return this.intervalMillis(millis1, millis2, false);
    }

    public boolean intervalMillis(int millis1, int millis2, boolean skipFirst) {
        int nowMillis = millis();
        if (this.startMillisInterval < 0) {
            this.startMillisInterval = nowMillis;
            if (skipFirst) {
                this.startMillisInterval -= millis1;
            }
        }

        if (this.currentInterval == 0 && nowMillis < this.startMillisInterval + millis1) {
            return true;
        } else if (this.currentInterval == 0) {
            this.currentInterval = 1;
            this.startMillisInterval = nowMillis;
            return false;
        } else if (this.currentInterval == 1 && nowMillis < this.startMillisInterval + millis2) {
            return false;
        } else {
            this.currentInterval = 0;
            this.startMillisInterval = nowMillis;
            return true;
        }
    }
}
