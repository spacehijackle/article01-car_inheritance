package inheritance.main;

import java.util.function.BiConsumer;

import inheritance.car.ACMode;
import inheritance.car.Car;
import inheritance.car.power.ElectricMotor;
import inheritance.monitor.Dashboard;

/**
 * 電気自動車走行クラス
 * 
 * @author t.yoshida
 */
public class RunWithElectricMotor
{
    public static void main(String[] args)
    {
        int distanceToGo = 300; // 目的地までの距離(km)

        // エネルギー消費イベントハンドラ
        BiConsumer<Car, Integer> onEnergyConsumed = (car, mileage) ->
        {
            // 走行状況の表示
            Dashboard.showStatus(car, distanceToGo, mileage);

            // 表示インターバル
            try { Thread.sleep(100); } catch(InterruptedException ex) { }
        };

        //
        // 電気自動車で目的地まで走らせる
        //
        var motorCar = new Car
        (
            new ElectricMotor(60, 6.2d)
            {
                {
                    fillUp();  // 満充電
                }
            }
        );
        motorCar.changeMode(ACMode.HEATING);  // 空調モードをヒーターONに変更
        motorCar.move(distanceToGo, onEnergyConsumed);
    }
}
