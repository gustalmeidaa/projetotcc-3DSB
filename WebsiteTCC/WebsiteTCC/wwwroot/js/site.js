var line = 1;
function addInput(formulario) {
    var newdiv = document.createElement('div');
    newdiv.innerHTML += ' <input  id="' + line + '" type="text" value="Remover" class="btn btn-danger" onclick="removerInput(this)" style="float:left;"readonly/>';
    newdiv.innerHTML += '<input type="text" class="form-control"  id="ingrediente-' + line + '" placeholder="Insira o ingrediente" style="width:198px;height:38px;"/>';
    newdiv.setAttribute("id", "campo" + line);
    document.getElementById("formulario").appendChild(newdiv);
    line++;
}
addInput('lines');
var num = 1;
function removerInput(button) {
    let number = button.id
    let row = document.getElementById('campo' + number)
    row.remove();
};
