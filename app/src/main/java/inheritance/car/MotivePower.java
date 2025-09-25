package inheritance.car;

/**
 * 動力源クラス
 * 
 * @apiNote このクラスを継承し、具体的な動力源を実装する。
 */
public abstract class MotivePower
{
    /** 容量 */
    private final double capacity;

    /** 残量 */
    private double remains;

    /** エネルギー消費率 */
    private double consumptionRate;

    public MotivePower(double capacity, double consumptionRate)
    {
        this.capacity = capacity;
        this.consumptionRate = consumptionRate;
    }

    /** 動力源名を返す。*/
    public abstract String powerSource();

    /** 容量を返す。*/
    public double capacity() { return capacity; }

    /** 残量を返す。*/
    public double remains() { return remains; }

    /** 満タンにする。*/
    public void fillUp()
    {
        this.remains = capacity;
    }

    /** エネルギーが空かどうか返す。*/
    public boolean isEmpty()
	{
		return (remains <= 0);
	}

    /**
     * 1km走行した際のエネルギーを消費する。
     */
	public void consumeEvery1km()
	{
		remains -= 1.0d / consumptionRate;
        if(remains < 0) remains = 0;
	}

    /**
     * 容量に指定分の空きがあるか否かを返す。
     * 
     * @param increment 注入量（正の数）
     * @return true: 空きあり、false: 空きなし
     */
    protected final boolean hasSpace(double increment)
    {
        if(increment <= 0) return false;

        return (remains + increment < capacity);
    }

    /**
     * 指定分のエネルギーを注入する。
     * 
     * @param increment 注入量
     * @return true: 注入成功、false: 注入失敗
     */
    protected final boolean injectEnergyIfPossible(double increment)
    {
        if(hasSpace(increment))
        {
            remains += increment;
            return true;
        }

        return false;
    }

    /**
     * 消費率（燃費）を変更する。
     * 
     * @param newRate 消費率（燃費）
     */
    protected final void changeConsumptionRate(double newRate)
    {
        this.consumptionRate = newRate;
    }

    /** モード変更イベント処理 */
    protected abstract void onModeChanged(ACMode mode);
}
