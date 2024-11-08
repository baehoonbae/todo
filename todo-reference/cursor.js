/*--------------------
Utils
--------------------*/
const scale = (v, x1, y1, x2, y2) => (v - x1) * (y2 - x2) / (y1 - x1) + x2;
const lerp = (a, b, t) => a + t * (b - a)

/*--------------------
Settings
--------------------*/
const settings = {
    backgroundColor: '#fff',
    letters: 'ggggggggggggg',
    minDistance: 14,
    resetTimer: 2000,
}

/*--------------------
Setup
--------------------*/
const canvas = document.getElementById('canvas')
const ctx = canvas.getContext('2d')
const devicePixelRatio = window.devicePixelRatio || 1
ctx.scale(devicePixelRatio, devicePixelRatio)  // 최초 한 번만 호출
const win = {
    w: window.innerWidth,
    h: window.innerHeight
}
const mouse = {
    x: win.w / 2,
    y: win.h / 2,
}
let time = 0
const chain = []
const letters = settings.letters.split('').reverse()
for (let i = 0; i < letters.length; i++) {
    chain.push({ letter: letters[i], x: 0, y: 0 })
}
const image = new Image();
image.src = 'img/mushroom.png';
image.onload = () => {
    animate();
}


/*--------------------
Resize
--------------------*/
const onResize = () => {
    win.w = window.innerWidth
    win.h = window.innerHeight
    canvas.width = win.w * devicePixelRatio
    canvas.height = win.h * devicePixelRatio
    canvas.style.width = `${win.w}px`
    canvas.style.height = `${win.h}px`
}
onResize()


/*--------------------
MouseMove
--------------------*/
let isMouseMoving = false
let timeoutID
const onMouseMove = (e) => {
    isMouseMoving = true
    mouse.x = e.touches ? e.touches[0].clientX : e.clientX
    mouse.y = e.touches ? e.touches[0].clientY : e.clientY

    chain[0].x = mouse.x
    chain[0].y = mouse.y

    clearTimeout(timeoutID)
    timeoutID = setTimeout(() => {
        isMouseMoving = false
    }, settings.resetTimer)
}


/*--------------------
Listeners
--------------------*/
window.addEventListener('resize', onResize)
window.addEventListener('mousemove', onMouseMove)
window.addEventListener('touchmove', onMouseMove)


/*--------------------
Clear
--------------------*/
const clear = () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height)
}


/*--------------------
Draw
--------------------*/
const draw = () => {
    clear()

    chain.forEach((link, index) => {
        if (isMouseMoving) {
            ctx.drawImage(image, link.x - image.width / 2, link.y - image.height / 2, 30,30);


            if (index > 0) {
                let prevLink = chain[index - 1]
                let dx = link.x - prevLink.x
                let dy = link.y - prevLink.y
                let distance = Math.sqrt(dx * dx + dy * dy)

                if (distance > settings.minDistance) {
                    let ratio = settings.minDistance / distance
                    link.x = lerp(link.x, prevLink.x + dx * ratio, .4)
                    link.y = lerp(link.y, prevLink.y + dy * ratio, .4)
                }
            }
        } else {
            const theta = scale(index, 0, chain.length, .3, .06)

            link.x = lerp(link.x, mouse.x - (index + 1) * settings.minDistance, theta)
            link.y = lerp(link.y, mouse.y + Math.sin(time * .3 + index * .5) * 3, theta)
            ctx.drawImage(image, link.x, link.y,30,30);
        }
    })
}
/*--------------------
Animate
--------------------*/
const animate = () => {
    time += 0.1

    clear()
    requestAnimationFrame(animate)

    draw()
}

/*--------------------
드래그 앤 드롭
--------------------*/
const todoCards = document.querySelectorAll('.todo-card');
const todoContainer = document.getElementById('todoContainer');

todoCards.forEach(card => {
    card.addEventListener('dragstart', (e) => {
        e.dataTransfer.setData('text/plain', e.target.innerHTML); // 카드 내용을 저장
        e.dataTransfer.effectAllowed = 'move'; // 드래그 효과 설정
        setTimeout(() => {
            e.target.classList.add('invisible'); // 드래그하는 카드 숨기기
        }, 0);
    });

    card.addEventListener('dragend', (e) => {
        e.target.classList.remove('invisible'); // 드래그가 끝나면 카드 보이기
    });
});

todoContainer.addEventListener('dragover', (e) => {
    e.preventDefault(); // 드롭 가능하게 설정
});

todoContainer.addEventListener('drop', (e) => {
    e.preventDefault();
    const draggedContent = e.dataTransfer.getData('text/plain'); // 드래그한 카드 내용 가져오기
    const target = e.target.closest('.todo-card'); // 드롭된 카드 찾기
    if (target) {
        const newCard = document.createElement('div'); // 새 카드 생성
        newCard.className = 'todo-card';
        newCard.draggable = true; // 새 카드도 드래그 가능하게 설정
        newCard.textContent = draggedContent; // 드래그한 내용 설정
        todoContainer.insertBefore(newCard, target); // 드롭된 카드 앞에 새 카드 삽입

        // 새로운 카드에 드래그 이벤트 추가
        newCard.addEventListener('dragstart', (e) => {
            e.dataTransfer.setData('text/plain', e.target.innerHTML);
            e.dataTransfer.effectAllowed = 'move';
            setTimeout(() => {
                e.target.classList.add('invisible');
            }, 0);
        });

        newCard.addEventListener('dragend', (e) => {
            e.target.classList.remove('invisible');
        });
    }
});
animate()