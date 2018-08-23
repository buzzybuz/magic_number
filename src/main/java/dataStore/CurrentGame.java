package dataStore;

import java.util.*;

/**
 * данные о текущей игре
 */

public class CurrentGame {

    private int[] goalNumeral;  // 4 цифры загаданного числа
    private int[] userNumeral;  // 4 цифры числа веденного пользователем
    private Date startTime;     // дата и время начала игры
    private Date totalTime;     // продолжительность игры
    private long start;         // дата и время начала игры в мсек.
    private long end;           // дата и время окончания игры в мсек.
    private ArrayList<Attempt> attempts;

    public CurrentGame() {
        goalNumeral = new int[4];
        userNumeral = new int[4];
        attempts = new ArrayList<>();
    }

    public ArrayList<Attempt> getAttempts() {
        return attempts;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getTotalTime() {
        return totalTime;
    }

    public void newGame() {
        startTime=new Date();
        start = System.currentTimeMillis();
        newNumber();
        attempts.clear();
    }

    /**
     * генерация нового числа
     */
    private void newNumber() {
        // используестя LinkedList для набора цифр 0-9
        // рандомом выбирается цифра по индексу и удалается из списка это значение
        // для убирания повторов при генерации следующей цифры
        LinkedList<Integer> numerals = new LinkedList<>();
        for (int i = 0; i <= 9; i++) {
            numerals.add(i);
        }
        Random random = new Random();
        //цифра
        int numeral;

        //first numeral
        numeral = random.nextInt(8) + 1;
        goalNumeral[0] = numeral;
        numerals.remove((Integer) numeral);

        //second numeral
        numeral = numerals.get(random.nextInt(8));
        goalNumeral[1] = numeral;
        numerals.remove((Integer) numeral);

        //third numeral
        numeral = numerals.get(random.nextInt(7));
        goalNumeral[2] = numeral;
        numerals.remove((Integer) numeral);

        //four numeral
        numeral = numerals.get(random.nextInt(6));
        goalNumeral[3] = numeral;
    }

    /**
     * введенные пользователем цифры проверяются на наличие повторов
     * @param userNumber - введенное пользователем число
     * @return = true если нет повторов
     */
    public boolean setUserNumber(int userNumber) {

        // для проверки повторов исрользуется HashSet, его функция add()
        // так как HashSet записывает данные не по порядку,
        // то цифры сохраняются в поле userNumeral[]
        Integer numeral;    //цифра - объект для хешсета
        int remainder;      // отаток от деления предыдущего порядка
        HashSet<Integer> hashSet=new HashSet<>();

        //1
        numeral = userNumber / 1000;
        userNumeral[0]=numeral;
        hashSet.add(numeral);
        remainder = userNumber % 1000;
        //2
        numeral = remainder / 100;
        userNumeral[1]=numeral;
        if (!hashSet.add(numeral)) {
            return false;
        }
        remainder = userNumber % 100;
        //3
        numeral = remainder / 10;
        userNumeral[2]=numeral;
        if (!hashSet.add(numeral)) {
            return false;
        }
        remainder = userNumber % 10;
        //4
        userNumeral[3]=remainder;
        return hashSet.add(remainder);
    }

    /**
     * сохраняет данные о раунде игры
     * @return = true если пользователь угадал число
     */
    public boolean setAttempt() {
        // число юзера
        int userNumber;
        // число полных совпадений (бык)
        int guess=0;
        // число совпадений без учета позиции (корова)
        int guessWithoutPosition=0;

        userNumber = 1000 * userNumeral[0] + 100 * userNumeral[1] + 10 * userNumeral[2] + userNumeral[3];

        for(int i=0; i<4;i++){
            for(int j=0; j<4;j++) {
                if(userNumeral[i]==goalNumeral[j]){
                    guessWithoutPosition++;
                    if(i==j){
                        guess++;
                    }
                }
            }
        }

        attempts.add(new Attempt(userNumber,guess,guessWithoutPosition));

        return (guess == 4);
    }

    /**
     * завершение игры, запись времени окончания
     */
    public void guessed(){
        end = System.currentTimeMillis();
        totalTime=new Date(end-start);
    }
}
