/**
Calculator program by Kttra

The program is built assuming that there will be spaces between the operator and operands in the input. It can
accept fractions, negative numbers, and positive numbers. The program can take in a string of the form
"1 + 2 =" and produces the result.

Details:
1. Simple arithmetic operators should be supported (i.e. +, -, *, /)
2. At least two operands are supported.
3. Operands can be fractional or whole numbers.
4. Operands can be negative or positive.
**/


fun main() {
    //Change input here
    val userInput = "1 + -4.5 + 10/2 - 10 ="

    //Send the user input to be analyzed and calculated
    val output = startParsing(userInput)
    println("Your answer is: ${userInput} ${output}")
}

//Analyze the string given by the user
fun startParsing(userInput: String): Double{
    //Convert userInput into a list of chars
    val fullList = mutableListOf<String>()
    val operationList = mutableListOf<String>()

    var tempStr = ""
    //Analyze the chars
    for(char in userInput){
        //Get new value if there is a space
        if(char == ' '){
            fullList.add(tempStr)
            tempStr = ""
        }
        //Keep adding to the str until we hit a space
        else{
            tempStr+= char
        }
    }

    //Separate the fullList to a list of numbers/operators
    for(str in fullList){
        var strValue = str.toDoubleOrNull()

        //If null, and it isn't length 1, it is unexpected (ex: 1+1,1-1,1/1,1*2)
        //So accounts input where there is no spaces but there is an operator between it
        if(strValue == null && str.length != 1){
            var unexpectedInput = ConvertUnexpectedInput(str)
            operationList.add(unexpectedInput)
        }
        //Operator
        else if(strValue == null){
            operationList.add(str)
        }
        //It is a number
        else{
            operationList.add(str)
        }
    }

    //calculate the results
    return calc(operationList).toDouble()
}

//Finally calculate the string
fun calc(operatorList: MutableList<String>): String{
    var i = 0

    //Prioritize multiplication and division
    while(true){
        //We reached the end of the list
        if(i == operatorList.size){
            break
        }
        if(operatorList[i] == "*" || operatorList[i] == "/"){
            //Remove the operator and the 2nd operand, update the 1st operand
            operatorList[i-1] = multDiv(operatorList[i-1],operatorList[i+1],operatorList[i])
            operatorList.removeAt(i)
            operatorList.removeAt(i)

            //Reset index
            i = 0
        }
        //A number is found, go to next index
        else{
            ++i
        }
    }

    i = 0

    //Continue on to addition and subtraction
    while(true){
        //We reached the end of the list
        if(i == operatorList.size){
            break
        }
        if(operatorList[i] == "+" || operatorList[i] == "-"){
            //Remove the operator and the 2nd operand, update the 1st operand
            operatorList[i-1] = addSub(operatorList[i-1],operatorList[i+1],operatorList[i])
            operatorList.removeAt(i)
            operatorList.removeAt(i)

            //Reset index
            i = 0
        }
        //Number is found, go to next index
        else{
            ++i
        }
    }

    return operatorList[0]
}

//Switch statement between addition or substraction
fun addSub(num1: String, num2: String, operationStr: String): String{
    var result: Double = 0.0

    when(operationStr) {
        "+" -> result = num1.toDouble() + num2.toDouble()
        "-" -> result = num1.toDouble() - num2.toDouble()
    }

    return result.toString()
}

//Switch statement between multiplication or Division
fun multDiv(num1: String, num2: String, operationStr: String): String{
    var result: Double = 0.0

    when(operationStr) {
        "*" -> result = num1.toDouble() * num2.toDouble()
        "/" -> result = num1.toDouble() / num2.toDouble()
    }

    return result.toString()
}

//Converts unexpected input like 1/2, 1*3, 1-2, 1+3 (input with no spaces)
//Does not account for multiple in a row as in 1+1+1, 4/5-3
fun ConvertUnexpectedInput(str: String): String{
    var num1 = ""
    var num2 = ""
    var operatorChar: Char = '?'
    var result = 0.0
    var num1Completed = false

    //Get the two numbers and the operator
    for(char in str){
        if(char == '+' || char == '-' || char == '/' || char == '*') {
            operatorChar = char
            num1Completed = true
        }
        else if(!num1Completed && operatorChar == '?'){
            num1 += char
        }
        else if(num1Completed){
            num2 += char
        }
    }

    //Simplify the statement
    when(operatorChar) {
        '+' -> result = num1.toDouble() + num2.toDouble()
        '-' -> result = num1.toDouble() - num2.toDouble()
        '/' -> result = num1.toDouble() / num2.toDouble()
        '*' -> result = num1.toDouble() * num2.toDouble()
    }

    return result.toString()
}