package inheritance.monitor;

import inheritance.car.Car;

/**
 * 走行距離等をコンソールに表示するクラス
 * 
 * @author t.yoshida
 */
public class Dashboard
{
    /**
     * 走行状況を表示する。
     * 
     * @param car 車
     * @param distanceToGo 目的地までの距離(km)
     * @param mileage 走行距離(km)
     */
    public static void showStatus(Car car, int distanceToGo, int mileage)
    {
        // コンソールクリア
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.printf
        (
            "動力源: %s%n空調Mode: %s%n走行距離: %s %.0fkm%n動力残量: %s %.2f%%%n",
            car.power().powerSource(),
            car.mode(),
            visualizeRatio(mileage, distanceToGo),
            (double)mileage,
            visualizeRatio(car.power().remains(), car.power().capacity()),
            (car.power().remains() / car.power().capacity()) * 100.0d
        );
    }

    /** 進捗度をバーで表示する。*/
    private static String visualizeRatio(double progress, double total)
    {
        int barLength = 50; // プログレスバーの長さ
        int filledLength = (int)(barLength * progress / total);

        StringBuilder bar = new StringBuilder();
        for(int i=0; i<filledLength; i++)
        {
            bar.append("█");  // 進捗部分
        }
        for(int i=filledLength; i<barLength; i++)
        {
            bar.append("░");  // 未進捗部分
        }

        return String.format("[%s]", bar.toString());
    }
}
