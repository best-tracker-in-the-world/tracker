export interface LongPressOptions {
  delay?: number
  progressSteps?: number
  onStart?: () => void
  onEnd?: (duration: number, completed: boolean) => void
  onProgress?: (progress: number) => void
  preventDefault?: boolean
  disabled?: boolean
}

export function useLongPress(callback: (event?: Event) => void, options: LongPressOptions = {}) {
  const {
    delay = 700,
    progressSteps = 20,
    onStart = () => {},
    onEnd = () => {},
    onProgress = () => {},
    preventDefault = true,
    disabled = false
  } = options

  const isPressed = ref(false)
  const progress = ref(0)
  const isCompleted = ref(false)
  
  let timeout: ReturnType<typeof setTimeout> | null = null
  let progressInterval: ReturnType<typeof setInterval> | null = null
  let startTime = 0
  let triggerEvent: Event | null = null

  const cleanup = () => {
    if (timeout) {
      clearTimeout(timeout)
      timeout = null
    }
    if (progressInterval) {
      clearInterval(progressInterval)
      progressInterval = null
    }
  }

  const start = (event: Event) => {
    if (disabled || isPressed.value) return
    
    if (preventDefault) {
      event.preventDefault()
    }
    
    triggerEvent = event
    startTime = Date.now()
    isPressed.value = true
    progress.value = 0
    isCompleted.value = false
    
    onStart()
    
    const stepDuration = delay / progressSteps
    let currentStep = 0
    
    progressInterval = setInterval(() => {
      currentStep++
      const newProgress = Math.min(currentStep / progressSteps, 1)
      progress.value = newProgress
      onProgress(newProgress)
      
      if (newProgress >= 1) {
        clearInterval(progressInterval!)
        progressInterval = null
      }
    }, stepDuration)
    
    timeout = setTimeout(() => {
      if (isPressed.value) {
        isCompleted.value = true
        callback(triggerEvent!)
      }
    }, delay)
  }

  const end = () => {
    if (!isPressed.value) return
    
    const duration = Date.now() - startTime
    const wasCompleted = isCompleted.value
    
    cleanup()
    
    if (!wasCompleted && progress.value > 0) {
      const fadeSteps = 10
      const fadeInterval = setInterval(() => {
        progress.value = Math.max(progress.value - (1 / fadeSteps), 0)
        if (progress.value <= 0) {
          clearInterval(fadeInterval)
        }
      }, 20)
    }
    
    isPressed.value = false
    onEnd(duration, wasCompleted)
    
    if (wasCompleted) {
      setTimeout(() => {
        isCompleted.value = false
        progress.value = 0
      }, 200)
    }
  }

  const cancel = () => {
    cleanup()
    isPressed.value = false
    isCompleted.value = false
    progress.value = 0
    onEnd(0, false)
  }

  const handlers = computed(() => ({
    onPointerdown: start,
    onPointerup: end,
    onPointerleave: cancel,
    onPointercancel: cancel,
    onTouchstart: start,
    onTouchend: end,
    onTouchcancel: cancel,
    onMousedown: start,
    onMouseup: end,
    onMouseleave: cancel,
    onContextmenu: (e: Event) => preventDefault && e.preventDefault()
  }))

  const progressStyles = computed(() => ({
    strokeDasharray: `${progress.value * 283} 283`,
    transform: 'rotate(-90deg)',
    transition: isPressed.value ? 'stroke-dasharray 0.05s linear' : 'stroke-dasharray 0.2s ease-out'
  }))

  const ringStyles = computed(() => ({
    transform: `rotate(${progress.value * 360}deg)`,
    transition: isPressed.value ? 'transform 0.05s linear' : 'transform 0.2s ease-out'
  }))

  return {
    isPressed: readonly(isPressed),
    progress: readonly(progress),
    isCompleted: readonly(isCompleted),
    
    start,
    end,
    cancel,
    
    handlers,
    progressStyles,
    ringStyles
  }
}