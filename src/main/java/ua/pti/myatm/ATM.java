package ua.pti.myatm;
public class ATM {

    private double MoneyAmount=0;
    private Card cardInserted;
    private boolean isCardIn = false;
    private int count=0;
    private int cardId=0;
        
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM) throws NegativeAmount {
         //throw new UnsupportedOperationException("Not yet implemented");
        if(moneyInATM>=0)
            MoneyAmount += moneyInATM;
        else throw new NegativeAmount("You can't set ATM with negative amount");
    }
    
  
    // Возвращает количество денег в банкомате
    public double getMoneyInATM() {
         //throw new UnsupportedOperationException("Not yet implemented");
        return MoneyAmount;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) throws NoCardInserted{
         //throw new UnsupportedOperationException("Not yet implemented");        
        try{
            isCardIn = true;            
            cardInserted = card;
            
            if(cardId!=card.getID()){
                count=0;
            }
            
            if(cardInserted.isBlocked()||!cardInserted.checkPin(pinCode))
            {
                count+=1;
                if (count>=3){
                    card.block();
                }               
                return false;
            }
            else
            {
            return true;
            }            
        }
        catch (NullPointerException e){throw new NoCardInserted("No card inserted");}
    }
    
    
    //Возвращает сколько денег есть на счету
    public double checkBalance() throws NoCardInserted{
         //throw new UnsupportedOperationException("Not yet implemented");
        try{
            Account acc = cardInserted.getAccount();
            return acc.getBalance();
        }
        catch (NullPointerException e){throw new NoCardInserted("No card inserted");}
        }        
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NotEnoughMoneyInAccount, NotEnoughMoneyInATM, NoCardInserted{
         //throw new UnsupportedOperationException("Not yet implemented");
        try{
            Account acc = cardInserted.getAccount();
            if (amount<MoneyAmount)
                {
                if(amount<acc.getBalance())
                    {
                    MoneyAmount -= amount;
                    return acc.withdrow(amount);
                    }
                else throw new NotEnoughMoneyInAccount("No enough money in account");
                }
            else throw new NotEnoughMoneyInATM("No enough money in ATM");
        }
        catch (NullPointerException e){throw new NoCardInserted("No card inserted");}
    }
      

}
