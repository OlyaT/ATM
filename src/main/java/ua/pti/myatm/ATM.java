package ua.pti.myatm;
public class ATM {

    private double MoneyAmount=0;
    private Card CardInserted;
        
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
         //throw new UnsupportedOperationException("Not yet implemented");
        MoneyAmount += moneyInATM;
    }

    // Возвращает количество денег в банкомате
    public double getMoneyInATM() {
         //throw new UnsupportedOperationException("Not yet implemented");
        return MoneyAmount;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode){
         //throw new UnsupportedOperationException("Not yet implemented");        
        try{
            if(card.isBlocked()||!card.checkPin(pinCode))
                return false;
            else
                CardInserted=card;
        }
        catch (NullPointerException e){System.err.println("Wrong PIN or card is blocked");}
        return true;
    }
    
    
    //Возвращает сколько денег есть на счету
    public double checkBalance(){
         //throw new UnsupportedOperationException("Not yet implemented");
        try{
            Account acc = CardInserted.getAccount();
            return acc.getBalance();
        }
        catch (NullPointerException e){System.err.println("No card Inserted");}
        return 0;
        }        
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount){
         //throw new UnsupportedOperationException("Not yet implemented");
        try{
            Account acc = CardInserted.getAccount();
            if (amount<MoneyAmount)
                {
                if(amount<acc.getBalance())
                    {
                    MoneyAmount -= acc.withdrow(amount);
                    return amount;
                    }
                else throw new UnsupportedOperationException("NotEnoughMoneyInAccount");
                }
            else throw new UnsupportedOperationException("NotEnoughMoneyInATM");
            }
        catch (NullPointerException e){System.err.println("There is no card inserted in ATM");}
        catch (UnsupportedOperationException e){System.err.println(e);}
        return 0;
    }

}
