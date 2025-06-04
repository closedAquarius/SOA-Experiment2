import { Card, CardContent } from "@/components/ui/card"
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"

export function CarouselDemo() {
    const imgs = [
        "images/mainBanner_fish.png",
        "images/mainBanner_dogs.png",
        "images/mainBanner_reptiles.png",
        "images/mainBanner_cat.png",
        "images/mainBanner_birds.png"
    ]
  return (
    <Carousel className="w-full max-w-lg">
      <CarouselContent>
        {Array.from({ length: 5 }).map((_, index) => (
          <CarouselItem key={index}>
            <div className="p-1">
              <Card>
                    <CardContent className="flex flex-col items-center justify-center gap-5">
                    <img src={imgs[index]} className="rounded-md"></img>
                    <span className="font-bold">{ index + 1}</span>            
                </CardContent>
              </Card>
            </div>
                
          </CarouselItem>
        ))}
      </CarouselContent>
      <CarouselPrevious />
      <CarouselNext />
    </Carousel>
  )
}
