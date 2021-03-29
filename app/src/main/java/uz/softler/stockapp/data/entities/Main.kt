fun main() {
    var input: String = readLine()!!.trim().filter { !it.isWhitespace() }

    if (input.filter { it != '-' && it != '(' && it != ')' && it != '+' }.length != 11) {
        print("error")
        return
    }

    if (input[0] == '+') {
        if (input[1] == '7' || input[1] == '8') {
            input = input.replace("+7", "8")
            input = input.replace("+8", "8")
        } else {
            print("error")
            return
        }
    } else {
        if (input[0] != '8') {
            print("error")
            return
        }
    }

    if (input.contains('(')) {
        if (!input.contains(')')) {
            print("error")
            return
        }
        if (input[1] != '(' && input[5] != ')') {
            print("error")
            return
        }
    }

    if (input.contains(')')) {
        if (!input.contains('(')) {
            print("error")
            return
        }
        if (input[1] != '(' && input[5] != ')') {
            print("error")
            return
        }
    }

    input.forEachIndexed { index: Int, char: Char ->
        if (index != 0 && char == '+') {
            print("error")
            return
        }

        if (char == '-') {
            if (index == 0 || index == input.length - 1) {
                print("error")
                return
            }
            if (!input[index - 1].isDigit() || !input[index + 1].isDigit()) {
                print("error")
                return
            }
            if (input.contains('(') && index in 1..4) {
                print("error")
                return
            }
        }
    }

    input = input.replace("-", "")
    input = input.replace("(", "")
    input = input.replace(")", "")

    input.forEach {
        if (!it.isDigit()) {
            print("error")
            return
        }
    }

    input = input[0] + " (" + input.subSequence(1, 4) + ") " + input.subSequence(4, 7) +
            "-" + input.subSequence(7, 9) + "-" + input.subSequence(9, 11)

    print(input)
}